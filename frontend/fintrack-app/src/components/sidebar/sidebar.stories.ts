import type { Meta, StoryObj } from '@storybook/angular';
import { Sidebar } from './sidebar.component';

const meta: Meta<Sidebar> = {
  title: 'Components/Sidebar',
  component: Sidebar,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<Sidebar>;

export const Primary: Story = {
  args: {},
};
