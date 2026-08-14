import type { Meta, StoryObj } from '@storybook/angular';
import { ClockComponent } from './dashboard-clock.component';

const meta: Meta<ClockComponent> = {
  title: 'Components/ClockComponent',
  component: ClockComponent,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<ClockComponent>;

export const Primary: Story = {
  args: {},
};
