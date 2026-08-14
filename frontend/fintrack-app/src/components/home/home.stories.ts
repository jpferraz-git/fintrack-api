import type { Meta, StoryObj } from '@storybook/angular';
import { Home } from './home.component';

const meta: Meta<Home> = {
  title: 'Components/Home',
  component: Home,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<Home>;

export const Primary: Story = {
  args: {},
};
