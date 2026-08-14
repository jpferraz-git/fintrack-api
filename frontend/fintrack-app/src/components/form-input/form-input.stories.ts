import type { Meta, StoryObj } from '@storybook/angular';
import { FormInput } from './form-input.component';

const meta: Meta<FormInput> = {
  title: 'Components/FormInput',
  component: FormInput,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<FormInput>;

export const Primary: Story = {
  args: {},
};
